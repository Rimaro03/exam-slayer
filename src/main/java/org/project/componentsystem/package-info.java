/**
 * This package contains the classes that are responsible for the component system.
 * <p>
 * This component system is inspired by Unity's component system.
 * <p>
 * GameObjects are entities in the game world that can have components attached to them.
 * <p>
 * Components are classes tha implement a specific behaviour, every component has a
 * reference to the GameObject it is attached to, plus a set of methods:
 * <p>
 * - {@link org.project.componentsystem.components.Component#start()} :
 * <p>
 *     Called when the component is enabled for the first time in the world. <p>
 * -{@link org.project.componentsystem.components.Component#update()}:
 * <p>
 *     Called every frame. <p>
 * -{@link org.project.componentsystem.components.Component#destory()}:
 * <p>
 *     Called when the component is removed from the world. <p>
 * -{@link org.project.componentsystem.components.Component#onEnable()}:
 * <p>
 *     Called when the component is enabled. <p>
 * -{@link org.project.componentsystem.components.Component#onDisable()}:
 * <p>
 *     Called when the component is disabled. <p>
 * Components can get references with {@link org.project.componentsystem.GameObject#getComponent(java.lang.Class)} or
 * by accessing another GameObject with {@link org.project.generation.Room#getGameObject(java.lang.String)} and then
 * getting the component from it. <p></p>
 *
 * To implement a new component, create a new class that extends {@link org.project.componentsystem.components.Component}
 * then override the methods you need to implement the behaviour you want.
 */

package org.project.componentsystem;