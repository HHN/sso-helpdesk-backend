import { useAppStore } from "@/store/app";
import { Location } from "@/models/Location";
import axios from "axios";
import { checkIfUserIsLoggedIn } from "./CheckLoginService";

const appStore = useAppStore();

export function fetchLocations() : void {
    appStore.resultList = [];
    checkIfUserIsLoggedIn(axios.get<Location[]>("/admin/rest/locations", {
        params: {}
    }).then(response => {
        appStore.locationList  = response.data;
    }));
    console.log("fetchUsers");
}
