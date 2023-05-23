import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'


export interface SearchFiltersState {
    movies: {
        searchValue?: string
        year?: number
        platform?: string
    },
    people: {
        searchValue?: string
    }
}

const initialState: SearchFiltersState = {
    movies: {
        searchValue: undefined,
        year: undefined,
        platform: undefined
    },
    people: {
        searchValue: undefined
    }

}

export const searchFilters = createSlice({
    name: 'settings',
    initialState,
    reducers: {
        setMovieSearchValue: (state, action: PayloadAction<string>) => {
            state.movies.searchValue = action.payload
        },
        setYear: (state, action: PayloadAction<number | undefined>) => {
            state.movies.year = action.payload
        },
        setPlatform: (state, action: PayloadAction<string | undefined>) => {
            state.movies.platform = action.payload
        },
        setPeopleSearchValue: (state, action: PayloadAction<string>) => {
            state.people.searchValue = action.payload
        },
    },
})

export const {
    setMovieSearchValue,
    setYear,
    setPlatform,
    setPeopleSearchValue
} = searchFilters.actions

export default searchFilters.reducer