import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { DataBase } from "../slice/settings";
import {Statistics} from "./movies";

export const peopleApi = createApi({
    reducerPath: 'peopleApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/' }),
    endpoints: (builder) => ({
        getPeople: builder.query<PeopleResponse, PeopleRequest>({
            query: (request) => `${request.movieDatabase}/people`,
        }),
        getPerson: builder.query<HumanDetailsResponse, HumanDetailsRequest>({
            query: (request) => `${request.movieDatabase}/people/${request.id}`,
        })
    }),
})

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useGetPeopleQuery, useGetPersonQuery } = peopleApi

export interface Human {
    id: string,
    firstName: string,
    secondName?: string,
    photoUrl: string,
}

export interface HumanDetails {
    id: string,
    firstName: string,
    secondName: string,
    photoUrl: string,
    //TODO change any to proper type
    birthday: any,
    placeOfBirth: string,
    deathDay?: string,
    description: string,
    functions: {
        director: MovieRoleData[],
        actor: MovieRoleData[]
    }
}

export interface MovieRoleData {
    filmid: string,
    title: string,
}

export interface PeopleResponse {
    data: {
        people: Human[]
        nextOffset: number,
        totalPages: number,
        totalRecords: number,
    },
    statistics: Statistics
}

export interface HumanDetailsResponse {
    data: HumanDetails,
    statistics: Statistics
}

export interface PeopleRequest {
    movieDatabase: DataBase
}

export interface HumanDetailsRequest {
    movieDatabase: DataBase
    id: string
}