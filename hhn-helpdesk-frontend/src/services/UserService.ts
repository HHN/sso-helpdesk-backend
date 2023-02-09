import { User } from "@/models/User";
import { useAppStore } from "@/store/app";
import axios from "axios";
import { showAlertMessage } from "./AlertMessageService";

const appStore = useAppStore();

export function fetchUsers(searchQuery: string) : void {
    appStore.resultList = [];
    axios.get<User[]>("/admin/rest/users", {
        params: {
            first: 0,
            max: 50,
            q: searchQuery
        }
    }).then(response => {
        appStore.resultList  = response.data;
    });
    console.log("fetchUsers");
}

export function resetCredential(keycloakId: string, seq: string) : void {
    if (!appStore.isWaiting) {
        appStore.isWaiting = true;
        axios.post("/admin/rest/reset", {
            id: keycloakId,
            seq: seq
        }).then(response => {
            appStore.isWaiting = false;
            appStore.showResetDialog = false;
    
            if (response.status.valueOf() == 200) {
                showAlertMessage("successfully reset credentials", "success");
            } else {
                showAlertMessage("error while resetting credentials", "error");
    
            }
        }).catch(error => {
            appStore.isWaiting = false;
            appStore.showResetDialog = false;
            showAlertMessage("error while resetting credentials", "error");
        });
    }
   
}


