// Utilities
import { User } from '@/models/User';
import { defineStore } from 'pinia'
import { Location } from '@/models/Location';
import { AuditEntry } from '@/models/AuditEntry';

export const useAppStore = defineStore('app', {
  state: () => ({
    //
    navigationDrawerOpen: false,
    resultList: [] as User[],
    searchQuery: "",
    locationList: [] as Location[],
    auditList: [] as AuditEntry[],
    isCreatingCredentials: false,
  }),

  actions: {
    toggleNavigationDrawer() {
      this.navigationDrawerOpen = !this.navigationDrawerOpen;
    },

  }
})
