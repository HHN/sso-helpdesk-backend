// Utilities
import { User } from '@/models/User';
import { defineStore } from 'pinia'
import { Location } from '@/models/Location';
import { AuditEntry } from '@/models/AuditEntry';
import { nullUser, UserOrNull } from '@/models/UserOrNull';
import { CurrentUser } from '@/models/CurrentUser';


export const useAppStore = defineStore('app', {
  state: () => ({
    //
    navigationDrawerOpen: false,
    resultList: [] as User[],
    searchQuery: "",
    locationList: [] as Location[],
    currentAuditList: [] as AuditEntry[],
    // based on 0
    currentAuditPage: 0,
    totalAuditPages: 1,
    isWaiting: false,
    currentResetUser: nullUser,
    showResetDialog: false,
    showAlertMessage: false,
    alertMessage: "Test",
    alertMessageType: "success",
    currentUser: new CurrentUser("N/A", [])
  }),

  actions: {
    toggleNavigationDrawer() {
      this.navigationDrawerOpen = !this.navigationDrawerOpen;
    },

  }
})
