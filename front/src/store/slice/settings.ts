import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

export type DataBase = "POSTGRESQL" | "MONGO_DB" | "INFLUX" | "FAKE_DATABASE"

export interface SettingsState {
    dataBase: DataBase
}

const initialState: SettingsState = {
    dataBase: "FAKE_DATABASE",
}

export const settingsSlice = createSlice({
    name: 'settings',
    initialState,
    reducers: {
        setDataBase: (state, action: PayloadAction<DataBase>) => {
            state.dataBase = action.payload
        },
    },
})

export const { setDataBase } = settingsSlice.actions

export default settingsSlice.reducer