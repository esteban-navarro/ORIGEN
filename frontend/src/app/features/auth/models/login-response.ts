import { AuthenticatedUser } from './authenticated-user';

export interface LoginResponse {

    accessToken: string;

    tokenType: string;

    expiresIn: number;

    user: AuthenticatedUser;

}
