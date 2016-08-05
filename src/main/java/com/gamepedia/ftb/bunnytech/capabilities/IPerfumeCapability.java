package com.gamepedia.ftb.bunnytech.capabilities;

import com.gamepedia.ftb.bunnytech.items.ItemPerfume;

import java.util.HashMap;

public interface IPerfumeCapability {
    /**
     * Gets the current perfumes for the entity.
     * @return A hash of the perfume, and the time remaining for the perfume.
     */
    HashMap<ItemPerfume.PerfumeTypes, Integer> getPerfumes();

    /**
     * Adds a perfume to the list of perfumes for the entity.
     * @param type The perfume
     * @param ticks The ticks for the perfume
     */
    void addPerfume(ItemPerfume.PerfumeTypes type, int ticks);

    /**
     * Removes a perfume from the list
     * @param type The perfume
     */
    void removePerfume(ItemPerfume.PerfumeTypes type);

    class DefaultImplementation implements IPerfumeCapability {
        private HashMap<ItemPerfume.PerfumeTypes, Integer> perfumes;

        public HashMap<ItemPerfume.PerfumeTypes, Integer> getPerfumes() {
            return perfumes;
        }

        public void addPerfume(ItemPerfume.PerfumeTypes type, int ticks) {
            if (perfumes.containsKey(type)) {
                int existingTicks = perfumes.get(type);
                int newTicks = ticks + existingTicks;
                perfumes.remove(type);
                perfumes.put(type, newTicks);
            } else {
                perfumes.put(type, ticks);
            }
        }

        public void removePerfume(ItemPerfume.PerfumeTypes type) {
            perfumes.remove(type);
        }
    }
}
