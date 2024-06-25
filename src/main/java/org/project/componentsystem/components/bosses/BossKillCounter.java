package org.project.componentsystem.components.bosses;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.menus.GameOverMenu;

public class BossKillCounter extends Component {
    int bossKilled;
    public BossKillCounter(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
        bossKilled = 0;
    }

    public BossKillCounter(GameObject gameObject) {
        this(gameObject, true);
    }

    public void addKill(){
        bossKilled++;
        if(bossKilled == 3){
            GameOverMenu gameOverMenu = (GameOverMenu) getGameObject().getComponent(GameOverMenu.class);
            gameOverMenu.enable(true);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
