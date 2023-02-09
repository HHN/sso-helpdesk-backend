import { User } from "./User";

export class UserOrNull {
    content: User | null = null;
}

export const nullUser = new UserOrNull();