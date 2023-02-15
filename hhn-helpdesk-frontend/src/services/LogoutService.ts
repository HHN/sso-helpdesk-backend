import { useAppStore } from "@/store/app";
import axios from "axios";
import { showAlertMessage } from "./AlertMessageService";

const appStore = useAppStore();

export function logout() {
    appStore.resultList = [];
    axios.post<void>("/admin/rest/logout").then(response => {
        showAlertMessage("Sie wurden erfolgreich abgemeldet. Laden Sie die Seite neu, um sich erneut anzumelden.", "success");
        
    }).catch(e => {
        console.log(e);
    });
    console.log("fetchCurrentUser");
}