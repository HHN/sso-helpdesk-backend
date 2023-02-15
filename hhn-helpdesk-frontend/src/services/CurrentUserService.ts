import { CurrentUser } from "@/models/CurrentUser";
import { User } from "@/models/User";
import { useAppStore } from "@/store/app";
import axios from "axios";
import { redirectToLogin } from "./CheckLoginService";

const appStore = useAppStore();

export function fetchCurrentUser() {
    appStore.resultList = [];
    axios.get<CurrentUser>("/admin/rest/user").then(response => {
        appStore.currentUser  = response.data;
        
    }).catch(e => {
        if (e.response.status === 403) {
            redirectToLogin();
        }
    });
    console.log("fetchCurrentUser");
}