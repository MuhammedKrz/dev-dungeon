package item.effects;

import core.Entity;
import core.components.PlayerComponent;
import core.components.VelocityComponent;
import systems.EventScheduler;

/**
 * Provides a mechanism to apply a temporary speed increase effect to an entity within the game.
 * Utilizing the EffectScheduler, this effect increases the entity's speed for a designated duration
 * before reverting it back to its original state. The implementation relies on scheduling both the
 * application of the speed increase and its subsequent reversal.
 */
public class SpeedEffect {
  private static final EventScheduler EVENT_SCHEDULER = EventScheduler.getInstance();
  private final float speedIncrease;
  private final int duration;

  /**
   * Initializes a new instance of the SpeedEffect with a specified increase in speed and duration.
   *
   * @param speedIncrease The amount to increase the entity's speed by.
   * @param duration The duration, in seconds, for which the speed increase is applied.
   */
  public SpeedEffect(float speedIncrease, int duration) {
    this.speedIncrease = speedIncrease;
    this.duration = duration;
  }

  /**
   * Applies a temporary speed increase to the target entity, then reverts its speed to normal after
   * the specified duration. The increase in speed is applied immediately, and its reversal will be
   * scheduled to occur after the duration expires.
   *
   * <p>TODO: Implement the applySpeedEffect method to schedule the speed increase and its
   * reversion.
   *
   * @param target The entity to which the speed effect will be applied.
   */
  public void applySpeedEffect(Entity target) {
      // Check if it's a player
      if (target.fetch(PlayerComponent.class).isEmpty()) {
          throw new UnsupportedOperationException(
              "Speed Portions can only be applied to player entities.");
      }


      target.fetch(core.components.VelocityComponent.class)
          // if the component is available
          .ifPresent(velocityComponent -> {
            // Storing the current Speed
            float defaultXVelocity = velocityComponent.xVelocity();
            float defaultYVelocity = velocityComponent.yVelocity();

            // increase the speed with random value
            velocityComponent.xVelocity(defaultXVelocity + 5.0f);
            velocityComponent.yVelocity(defaultYVelocity + 5.0f);

            // Set after a certain time the speed back
              EVENT_SCHEDULER.scheduleAction(
                  () -> {
                      velocityComponent.xVelocity(defaultXVelocity);
                      velocityComponent.yVelocity(defaultYVelocity);
                  },
                  duration * 1000L
              );
          });
  }
}
