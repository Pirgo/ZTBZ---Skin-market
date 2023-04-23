import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import {DataBase} from "../slice/settings";

export const moviesApi = createApi({
    reducerPath: 'moviesApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/' }),
    endpoints: (builder) => ({
        getMovies: builder.query<MoviesResponse, MoviesRequest>({
            query: (request) => `${request.movieDatabase}/movies`,
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
    id: number,
    title: string,
    productionYear: number,
    length: number,
    coverUrl: string,
    genres: MovieGenre[],
}

export interface MovieGenre {
    id: number,
    name: string,
}

export interface Platform {
    id: number,
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
}

export interface MovieDetails {
    id: number,
    title: string,
    platforms: Platform[],
    genres: MovieGenre[],
    productionYear: number,
    rating: number,
    plot: string,
    coverUrl: string,
    budget: number,
    length: number,
    //TODO actors and directors
}

export interface MovieDetailsResponse {
    data: MovieDetails,
    statistics: Statistics
}

export interface MovieDetailsRequest {
    movieDatabase: DataBase
    id: number,
}