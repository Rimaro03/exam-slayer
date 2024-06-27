package org.project.componentsystem.components.bosses;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.PlayerController;
import org.project.componentsystem.components.menus.GameOverMenu;
import org.project.core.Game;
import org.project.generation.wavecollapse.GenerationSettings;

/**
 * A component that keeps track of the number of bosses killed,
 * it needs to the attached to the player game object.
 */
public class BossKillCounter extends Component {
    int bossKilled;
    public BossKillCounter(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    public BossKillCounter(GameObject gameObject) {
        this(gameObject, true);
    }

    public void addKill(){
        bossKilled++;
        if(bossKilled == GenerationSettings.BOSS_ROOM_COUNT){
            GameOverMenu gameOverMenu = (GameOverMenu) Game.getCurrentLevel().findGameObject("GameOverMenu").getComponent(GameOverMenu.class);
            gameOverMenu.enable(true);
            Game.getCurrentLevel().findGameObject("Player").getComponent(PlayerController.class).setEnabled(false);
        }
    }

    @Override
    public void start() {
        Integer tmp = Game.getSavingIO().getInt("BossKilled");
        bossKilled = tmp != null ? tmp : 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void destory() {
        Game.getSavingIO().setInt("BossKilled", bossKilled);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
