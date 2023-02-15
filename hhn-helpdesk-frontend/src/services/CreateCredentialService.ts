import axios from "axios";
import { useAppStore } from "@/store/app";
import download from 'downloadjs';
import { fetchLocations } from "./LocationService";
import { checkIfUserIsLoggedIn } from "./CheckLoginService";


const appStore = useAppStore();

export function createCredentials(locationId: number, amount: number) : void {
    appStore.isWaiting = true;
    checkIfUserIsLoggedIn(axios.post("/admin/rest/create", {
        location: locationId,
        amount: amount
    }).then(response => {
        appStore.isWaiting = false;
        console.log("create credentials status code: "  +response.status.valueOf());
        const content = response.headers['content-type'];
        download(response.data, "credentials.pdf", content);
        fetchLocations();
        
    }));
}