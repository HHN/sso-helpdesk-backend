import { useAppStore } from "@/store/app";
import { AuditEntry } from "@/models/AuditEntry";
import axios from "axios";
import { checkIfUserIsLoggedIn } from "./CheckLoginService";

const appStore = useAppStore();

export function fetchAuditEntries() : void {
    appStore.resultList = [];
    checkIfUserIsLoggedIn(axios.get<AuditEntry[]>("/admin/rest/audits", {
        params: {}
    }).then(response => {
        appStore.auditList  = response.data;
    }));
    console.log("fetchAuditEntries");
}
