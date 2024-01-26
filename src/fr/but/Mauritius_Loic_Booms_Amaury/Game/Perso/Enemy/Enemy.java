package fr.but.Mauritius_Loic_Booms_Amaury.Game.Perso.Enemy;

import java.util.Objects;
import java.util.Random;

public class Enemy {
    private final String name;
    private final int hp_max;
    private int hp;
    private final int dgt;
    private final int protect;
    private int protection;

    public Enemy(String name, int hp, int dgt, int protect) {
        this.name = Objects.requireNonNull(name);
        this.hp_max = hp;
        this.hp = hp;
        this.dgt = dgt;
        this.protect = protect;
        this.setProtection(0);
    }
    //TODO enum pour les etats de combats (attack,protect,etc...)

    @Override
    public String toString() {
        return this.name + ": PV -> " + this.hp + ", Dgt ->" + this.dgt;
    }
    
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

	public int getDgt() {
		return dgt;
	}
	
	public EnemyAction action() {
        var r = new Random();
        var rand = r.nextInt(2);
        
        if(rand == 0) {
        	return EnemyAction.Attack;
        }else {
        	return EnemyAction.Protect;
        }
    }

	public int getMax_Hp() {
		return this.hp_max;
	}

	public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}

	public int getProtect() {
		return protect;
	}
}
