import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import {DataBase} from "../slice/settings";

export const moviesApi = createApi({
    reducerPath: 'moviesApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/' }),
    endpoints: (builder) => ({
        getMovies: builder.query<MoviesResponse, MoviesRequest>({
            query: ({searchText, year, platform, offset, movieDatabase, pageSize}) => {
                return {
                    url: `${movieDatabase}/movies`,
                    params: {searchText, year, platform, offset, pageSize}
                }
            }
        }),
        getMovie: builder.query<MovieDetailsResponse, MovieDetailsRequest>({
            query: (request) => `${request.movieDatabase}/movies/${request.id}`,
        })
    }),
})

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useGetMoviesQuery, useGetMovieQuery } = moviesApi

export interface Movie {
    id: string,
    title: string,
    productionYear: number,
    length: number,
    coverUrl: string,
    genres: MovieGenre[],
}

export interface MovieGenre {
    id: string,
    name: string,
}

export interface Platform {
    id: string,
    name: string,
    logoUrl: string,
}

export interface MoviesResponse {
    data: {
        movies: Movie[],
        nextOffset: number,
        totalPages: number,
        totalRecords: number,
    },
    statistics: Statistics
}

export interface Statistics {
    accessTime: number,
    dataBaseMemorySize: number
}

export interface MoviesRequest {
    movieDatabase: DataBase
    searchText?: string
    platform?: string
    year?: number
    offset: number
    pageSize: number
}

export interface MovieDetails {
    id: string,
    title: string,
    platforms: Platform[],
    genres: MovieGenre[],
    productionYear: number,
    rating: number,
    plot: string,
    coverUrl: string,
    budget: number,
    length: number,
    actors: [{
        id: string,
        name: string
    }],
    directors: [{
        id: string,
        name: string
    }]
}

export interface MovieDetailsResponse {
    data: MovieDetails,
    statistics: Statistics
}

export interface MovieDetailsRequest {
    movieDatabase: DataBase
    id: string,
}