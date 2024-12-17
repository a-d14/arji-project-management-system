import { configureStore, createSlice } from "@reduxjs/toolkit";

const initialUserState = {
    id: -1,
    username: '',
    roles: []
}

const userSlice = createSlice({
    name: 'user',
    initialState: initialUserState,
    reducers: {
        login(state, action) {
            console.log('here');
            state.id = action.payload.id;
            state.username = action.payload.username;
            state.roles = action.payload.roles;
            console.log(state);
        },
        logout(state, action) {
            state.id = -1;
            state.username = '';
            state.roles = [];
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
