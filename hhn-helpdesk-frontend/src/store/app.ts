// Utilities
import { User } from '@/models/User';
import { defineStore } from 'pinia'
import { Location } from '@/models/Location';
import { AuditEntry } from '@/models/AuditEntry';
import { nullUser, UserOrNull } from '@/models/UserOrNull';


export const useAppStore = defineStore('app', {
  state: () => ({
    //
    navigationDrawerOpen: false,
    resultList: [] as User[],
    searchQuery: "",
    locationList: [] as Location[],
    auditList: [] as AuditEntry[],
    isWaiting: false,
    currentResetUser: nullUser,
    showResetDialog: false,
    showAlertMessage: false,
    alertMessage: "Test",
    alertMessageType: "success"
  }),

  actions: {
    toggleNavigationDrawer() {
      this.navigationDrawerOpen = !this.navigationDrawerOpen;
    },

  }
})
