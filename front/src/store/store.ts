import { configureStore } from '@reduxjs/toolkit'
import settingsReducer from './slice/settings'
import storage from 'redux-persist/lib/storage';
import { persistReducer, persistStore } from 'redux-persist';
import thunk from 'redux-thunk';

const persistConfig = {
    key: 'root',
    storage,
}

const persistedReducer = persistReducer(persistConfig, settingsReducer)

export const store = configureStore({
    reducer: persistedReducer,
    middleware: [thunk]
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch

export const persistor = persistStore(store)
