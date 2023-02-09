// Utilities
import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', {
  state: () => ({
    //
    navigationDrawerOpen: false,
    resultList: [] as string[],
    searchQuery: "",
  }),

  actions: {
    toggleNavigationDrawer() {
      this.navigationDrawerOpen = !this.navigationDrawerOpen;
    },

  }
})
