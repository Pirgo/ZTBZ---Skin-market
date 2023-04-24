import {combineReducers, configureStore} from '@reduxjs/toolkit'
import settingsReducer from './slice/settings'
import storage from 'redux-persist/lib/storage';
import { persistReducer, persistStore } from 'redux-persist';
import thunk from 'redux-thunk';
import { moviesApi } from "./api/movies";
import {peopleApi} from "./api/people";

const persistConfig = {
    key: 'root',
    storage,
}

const rootReducer = combineReducers({
    settings: persistReducer(persistConfig, settingsReducer),
    [moviesApi.reducerPath]: moviesApi.reducer,
    [peopleApi.reducerPath]: peopleApi.reducer
})

export const store = configureStore({
    reducer: rootReducer,
    middleware: [thunk, moviesApi.middleware, peopleApi.middleware]
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch

export const persistor = persistStore(store)
