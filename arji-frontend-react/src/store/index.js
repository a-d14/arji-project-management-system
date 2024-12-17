import { configureStore, createSlice } from "@reduxjs/toolkit";

const loadState = () => {
    const storedState = localStorage.getItem("authState");
    return storedState ? JSON.parse(storedState) : { id: -1, username: '', roles: [] };
};

const initialUserState = loadState();

const userSlice = createSlice({
    name: 'user',
    initialState: initialUserState,
    reducers: {
        login(state, action) {
            state.id = action.payload.id;
            state.username = action.payload.username;
            state.roles = action.payload.roles;

            localStorage.setItem("authState", JSON.stringify(state));
        },
        logout(state, action) {
            state.id = -1;
            state.username = '';
            state.roles = [];

            localStorage.removeItem("authState");
        }
    }
});

const store = configureStore({
    reducer: {
        auth: userSlice.reducer
    }
});

export const authActions = userSlice.actions;
export default store;
