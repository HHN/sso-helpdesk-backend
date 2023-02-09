import { useAppStore } from "@/store/app";
import axios from "axios";

const appStore = useAppStore();

export function fetchUsers(searchQuery: string) : void {
    appStore.resultList = [];
    axios.get<string[]>("/admin/rest/users", {
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