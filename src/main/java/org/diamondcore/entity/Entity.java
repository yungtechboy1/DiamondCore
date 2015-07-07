package org.diamondcore.entity;

public abstract class Entity {
	
	public abstract String getName();
	public abstract double getHealth();
	public abstract double getMaxHealth();
	
	public abstract void setHealth();
	public abstract void setMaxHealth();
	
}
