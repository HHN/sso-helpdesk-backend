import { useAppStore } from "@/store/app";
import { AuditEntry } from "@/models/AuditEntry";
import axios from "axios";
import { checkIfUserIsLoggedIn } from "./CheckLoginService";
import { AuditPage } from "@/models/AuditPage";

const appStore = useAppStore();

export function fetchAuditEntries(page: number = 0) : void {
    appStore.resultList = [];
    checkIfUserIsLoggedIn(axios.get<AuditPage>("/admin/rest/audits", {
        params: {
            page: page
        }
    }).then(response => {
        appStore.currentAuditList  = response.data.content;
        appStore.currentAuditPage = response.data.number;
        appStore.totalAuditPages = response.data.totalPages;
    }));
    console.log("fetchAuditEntries");
}
