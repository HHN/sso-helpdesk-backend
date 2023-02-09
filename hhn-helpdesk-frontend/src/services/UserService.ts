import { User } from "@/models/User";
import { useAppStore } from "@/store/app";
import axios from "axios";

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
    axios.post("/admin/rest/reset", {
        id: keycloakId,
        seq: seq
    }).then(response => {
        if (response.status.valueOf() == 200) {
            alert("successfully reset credentials");
        } else {
            alert("error while resetting credentials");

        }
    });
}
