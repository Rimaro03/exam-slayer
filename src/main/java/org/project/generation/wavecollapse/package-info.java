/**
 * This package contains all the implemented functionalities
 * based ont the wave function collapse algorithm.
 * <p>
 * Wave Function Collapse is an algorithm inspired by the ideas of quantum mechanics.
 * According to one of such ideas, there's a set of possible states for each object.
 * Initially, every object is in a superposition of all possible states, in this stage every
 * object is in a state of max entropy, then for each iteration the object with less entropy is
 * chosen and collapsed to one of its possible states, this collapse propagates to the neighbours
 * bases on a set of rules, this process is repeated until all objects are collapsed.
 * <p>
 * For this implementation, the objects are {@link org.project.generation.wavecollapse.SuperRoom}
 * and the states are the room door configuration {@link org.project.generation.wavecollapse.RoomState}.
 * The rules are that a room can only have a door in a direction if the adjacent room has a door in the opposite direction.
 *
 * @see <a href="https://pvs-studio.com/en/blog/posts/csharp/1027/#:~:text=Wave%20Function%20Collapse%20is%20an%20algorithm%20inspired%20by%20the%20ideas,the%20elements%20of%20this%20set.">...</a>
 * @see <a href="https://robertheaton.com/2018/12/17/wavefunction-collapse-algorithm/">...</a>
 * @see <a href="https://en.wikipedia.org/wiki/Wave_function_collapse">...</a>
 */

package org.project.generation.wavecollapse;

