import { useSelector, useDispatch } from 'react-redux'
import { setDataBase } from "../../store/slice/settings";
import {RootState} from "../../store/store";
import {useEffect, useState} from "react";
import Chart from 'react-apexcharts'
import {styled} from "@stitches/react";

const ChartContainer = styled("div", {
    display: "flex",
    flexWrap: "wrap",
})

const Settings = () => {
    const dispatch = useDispatch()
    const selectedDataBase = useSelector((state: RootState) => state.settings.dataBase)
    const [isBenchmarkRunning, setIsBenchmarkRunning] = useState(false)
    const [getMoviesBasicData, setMoviesBasicData] = useState<any>([])
    const [getMoviesAdvancedData, setMoviesAdvancedData] = useState<any>([])
    const [getPeopleBasicData, setPeopleBasicData] = useState<any>([])
    const [getPeopleAdvancedData, setPeopleAdvancedData] = useState<any>([])
    const [postHuman, setPostHuman] = useState<any>([])
    const [postMovie, setPostMovie] = useState<any>([])
    const [getPeople, setGetPeople] = useState<any>()
    const [getMovie, setGetMovie] = useState<any>()
    const [deleteMovies, setDeleteMovies] = useState<any>([])
    const [deletePeople, setDeletePeople] = useState<any>([])



    const isSelected = (value: string) => {
        return value === selectedDataBase
    }

    const onClick = () => {
        setIsBenchmarkRunning(true)
    }

    useEffect(() => {
        const runBenchmark = async () => {
            const getMoviesBasicData = await Promise.all([
                fetch('http://localhost:8080/MONGO_DB/movies'),
                fetch('http://localhost:8080/POSTGRESQL/movies'),
                // fetch('http://localhost:8080/INFLUX/movies'),
            ]);

            const getMoviesAdvancedData = await Promise.all([
                fetch('http://localhost:8080/MONGO_DB/movies?platform=Hulu&year=2000'),
                fetch('http://localhost:8080/POSTGRESQL/movies?platform=Hulu&year=2000'),
                // fetch('http://localhost:8080/INFLUX/movies?platform=Hulu&year=2000'),
            ]);

            const getPeopleBasicData = await Promise.all([
                fetch('http://localhost:8080/MONGO_DB/people'),
                fetch('http://localhost:8080/POSTGRESQL/people'),
                // fetch('http://localhost:8080/INFLUX/people'),
            ]);

            const getPeopleAdvancedData = await Promise.all([
                fetch('http://localhost:8080/MONGO_DB/people?searchText=John'),
                fetch('http://localhost:8080/POSTGRESQL/people?searchText=John'),
                // fetch('http://localhost:8080/INFLUX/people'),
            ]);

            const postHuman = await Promise.all([
                fetch("http://localhost:8080/MONGO_DB/people", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        firstName: "test",
                        secondName: "benchmark",
                        photoUrl: "string",
                        birthday: "2023-06-06",
                        placeOfBirth: "string",
                        deathDay: "2023-06-06",
                        description: "string"
                    })
                }),
                fetch("http://localhost:8080/POSTGRESQL/people", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        firstName: "test",
                        secondName: "benchmark",
                        photoUrl: "string",
                        birthday: "2023-06-06",
                        placeOfBirth: "string",
                        deathDay: "2023-06-06",
                        description: "string"
                    })
                }),
                // fetch("http://localhost:8080/INFLUX/people", {
                //     method: "POST",
                //     headers: {
                //         "Content-Type": "application/json",
                //     },
                //     body: JSON.stringify({
                //         firstName: "test",
                //         secondName: "benchmark",
                //         photoUrl: "string",
                //         birthday: "2023-06-06",
                //         placeOfBirth: "string",
                //         deathDay: "2023-06-06",
                //         description: "string"
                //     })
                // })
            ])



            // convert the data to json
            const getMoviesBasicDataJson = await Promise.all(
                getMoviesBasicData.map(m => m.json())
            )
            const getMoviesAdvancedDataJson = await Promise.all(
                getMoviesAdvancedData.map(m => m.json())
            )
            const getPeopleBasicDataJson = await Promise.all(
                getPeopleBasicData.map(m => m.json())
            )
            const getPeopleAdvancedDataJson = await Promise.all(
                getPeopleAdvancedData.map(m => m.json())
            )
            const postPeopleDataJson = await Promise.all(
                postHuman.map(m => m.json())
            )

            const postMovie = await Promise.all([
                fetch("http://localhost:8080/MONGO_DB/movies", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        title: "string",
                        platformIds: [
                            "1"
                        ],
                        genreIds: [
                            "1"
                        ],
                        productionYear: 0,
                        rating: 0,
                        plot: "string",
                        coverUrl: "string",
                        budget: 0,
                        length: 0,
                        actors: [
                            {
                                id: postPeopleDataJson[0].data.id,
                                character: "string"
                            }
                        ],
                        directors: [
                            {
                                id: postPeopleDataJson[0].data.id
                            }
                        ]
                    })
                }),
                fetch("http://localhost:8080/POSTGRESQL/movies", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        title: "string",
                        platformIds: [
                            "1"
                        ],
                        genreIds: [
                            "1"
                        ],
                        productionYear: 0,
                        rating: 0,
                        plot: "string",
                        coverUrl: "string",
                        budget: 0,
                        length: 0,
                        actors: [
                            {
                                id: postPeopleDataJson[1].data.id,
                                character: "string"
                            }
                        ],
                        directors: [
                            {
                                id: postPeopleDataJson[1].data.id
                            }
                        ]
                    })
                }),
                // fetch("http://localhost:8080/INFLUX/movies", {
                //     method: "POST",
                //     headers: {
                //         "Content-Type": "application/json",
                //     },
                //     body: JSON.stringify({
                //         title: "string",
                //         platformIds: [
                //             "1"
                //         ],
                //         genreIds: [
                //             "1"
                //         ],
                //         productionYear: 0,
                //         rating: 0,
                //         plot: "string",
                //         coverUrl: "string",
                //         budget: 0,
                //         length: 0,
                //         actors: [
                //             {
                //                 id: postPeopleDataJson[2].data.id,
                //                 character: "string"
                //             }
                //         ],
                //         directors: [
                //             {
                //                 id: postPeopleDataJson[2].data.id
                //             }
                //         ]
                //     })
                // })
            ])

            const postMoviesDataJson = await Promise.all(
                postMovie.map(m => m.json())
            )

            const getHuman = await Promise.all([
                fetch(`http://localhost:8080/MONGO_DB/people/${postPeopleDataJson[0].data.id}`),
                fetch(`http://localhost:8080/POSTGRESQL/people/${postPeopleDataJson[1].data.id}`),
                // fetch(`http://localhost:8080/INFLUX/people/${postPeopleDataJson[2].data.id}`)
            ])

            const getMovie = await Promise.all([
                fetch(`http://localhost:8080/MONGO_DB/movies/${postMoviesDataJson[0].data.id}`),
                fetch(`http://localhost:8080/POSTGRESQL/movies/${postMoviesDataJson[1].data.id}`),
                // fetch(`http://localhost:8080/INFLUX/movies/${postMoviesDataJson[2].data.id}`)
            ])

            const deleteMovies = await Promise.all([
                fetch(`http://localhost:8080/MONGO_DB/movies/${postMoviesDataJson[0].data.id}`, {
                    method: "DELETE",
                }),
                fetch(`http://localhost:8080/POSTGRESQL/movies/${postMoviesDataJson[1].data.id}`, {
                    method: "DELETE",
                }),
                // fetch(`http://localhost:8080/INFLUX/movies/${postMoviesDataJson[2].data.id}`, {
                //     method: "DELETE",
                // })
            ])

            const deletePeople = await Promise.all([
                fetch(`http://localhost:8080/MONGO_DB/people/${postPeopleDataJson[0].data.id}`, {
                    method: "DELETE",
                }),
                fetch(`http://localhost:8080/POSTGRESQL/people/${postPeopleDataJson[1].data.id}`, {
                    method: "DELETE",
                }),
                // fetch(`http://localhost:8080/INFLUX/people/${postPeopleDataJson[2].data.id}`, {
                //     method: "DELETE",
                // })
            ])

            const getHumanData = await Promise.all(
                getHuman.map(h => h.json())
            )

            const getMovieData = await Promise.all(
                getMovie.map(h => h.json())
            )

            const deleteMoviesData = await Promise.all(
                deleteMovies.map(m => m.json())
            )

            const deletePeopleData = await Promise.all(
                deletePeople.map(m => m.json())
            )

            // set state with the result
            setMoviesBasicData(getMoviesBasicDataJson)
            setMoviesAdvancedData(getMoviesAdvancedDataJson)
            setPeopleBasicData(getPeopleBasicDataJson)
            setPeopleAdvancedData(getPeopleAdvancedDataJson)
            setPostHuman(postPeopleDataJson)
            setPostMovie(postMoviesDataJson)
            setDeleteMovies(deleteMoviesData)
            setDeletePeople(deletePeopleData)
            setGetMovie(getMovieData)
            setGetPeople(getHumanData)
            setIsBenchmarkRunning(false);
        }
        if(isBenchmarkRunning){
            runBenchmark()
                // make sure to catch any error
                .catch(console.error);
        }
    }, [isBenchmarkRunning])

    const getMoviesBasicDataChart = getMoviesBasicData.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET page movies",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getMoviesBasicData[0].statistics.accessTime, getMoviesBasicData[1].statistics.accessTime],
        }]
    } : null

    const getMoviesAdvancedDataChart = getMoviesAdvancedData.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET page movies with options",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getMoviesAdvancedData[0].statistics.accessTime, getMoviesAdvancedData[1].statistics.accessTime]
        }]
    } : null

    const getPeopleBasicDataChart = getPeopleBasicData.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET page people",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getPeopleBasicData[0].statistics.accessTime, getPeopleBasicData[1].statistics.accessTime]
        }]
    } : null

    const getPeopleAdvancedDataChart = getPeopleAdvancedData.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET page people with options",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getPeopleAdvancedData[0].statistics.accessTime, getPeopleAdvancedData[1].statistics.accessTime]
        }]
    } : null

    const postPeople = postHuman.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "POST person",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [postHuman[0].statistics.accessTime, postHuman[1].statistics.accessTime]
        }]
    } : null

    const postMoviesData = postMovie.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "POST movie",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [postMovie[0].statistics.accessTime, postMovie[1].statistics.accessTime]
        }]
    } : null

    const getMovieChart = postMovie.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET movie",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getMovie[0].statistics.accessTime, getMovie[1].statistics.accessTime]
        }]
    } : null

    const getPeopleChart = postMovie.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "GET person",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [getPeople[0].statistics.accessTime, getPeople[1].statistics.accessTime]
        }]
    } : null

    const deletePeopleChart = postMovie.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "Delete person",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [deletePeople[0].statistics.accessTime, deletePeople[1].statistics.accessTime]
        }]
    } : null

    const deleteMovieChart = postMovie.length > 0 ? {
        options: {
            chart: {
                id: 'apexchart-example',
            },
            xaxis: {
                //Add influx
                categories: ["Mongo", "Postgres"]
            },
            title: {
                text: "Delete movie",
            },
            colors: ['#00ff00', '#0000FF'],
            plotOptions: {
                bar: {
                    distributed: true, // this line is mandatory
                },
            },
        },
        series: [{
            name: 'access time',
            //Add influx
            data: [deleteMovies[0].statistics.accessTime, deleteMovies[1].statistics.accessTime]
        }]
    } : null


    return (
        <>
            <div>
                <input type="radio" value="POSTGRESQL" name="db" checked={isSelected("POSTGRESQL")} onChange={() => dispatch(setDataBase("POSTGRESQL"))}/> Postgres
                <input type="radio" value="MONGO_DB" name="db" checked={isSelected("MONGO_DB")} onChange={() => dispatch(setDataBase("MONGO_DB"))}/> Mongo
                <input type="radio" value="INFLUX" name="db" checked={isSelected("INFLUX")} onChange={() => dispatch(setDataBase("INFLUX"))}/> Influx
                <input type="radio" value="FAKE_DATABASE" name="db" checked={isSelected("FAKE_DATABASE")} onChange={() => dispatch(setDataBase("FAKE_DATABASE"))}/> Fake db
            </div>
            <div>
                <h1>Benchmark</h1>
                <button onClick={onClick} disabled={isBenchmarkRunning}>Run</button>
            </div>
            <ChartContainer>
                {getMoviesBasicDataChart !== null ? <Chart options={getMoviesBasicDataChart.options} series={getMoviesBasicDataChart.series} type="bar" width={500} height={320} /> : null }
                {getMoviesAdvancedDataChart !== null ? <Chart options={getMoviesAdvancedDataChart.options} series={getMoviesAdvancedDataChart.series} type="bar" width={500} height={320} /> : null }
                {getPeopleBasicDataChart !== null ? <Chart options={getPeopleBasicDataChart.options} series={getPeopleBasicDataChart.series} type="bar" width={500} height={320} /> : null }
                {getPeopleAdvancedDataChart !== null ? <Chart options={getPeopleAdvancedDataChart.options} series={getPeopleAdvancedDataChart.series} type="bar" width={500} height={320} /> : null }
                {postPeople !== null ? <Chart options={postPeople.options} series={postPeople.series} type="bar" width={500} height={320} /> : null }
                {postMoviesData !== null ? <Chart options={postMoviesData.options} series={postMoviesData.series} type="bar" width={500} height={320} /> : null }
                {getMovieChart !== null ? <Chart options={getMovieChart.options} series={getMovieChart.series} type="bar" width={500} height={320} /> : null }
                {getPeopleChart !== null ? <Chart options={getPeopleChart.options} series={getPeopleChart.series} type="bar" width={500} height={320} /> : null }
                {deleteMovieChart !== null ? <Chart options={deleteMovieChart.options} series={deleteMovieChart.series} type="bar" width={500} height={320} /> : null }
                {deletePeopleChart !== null ? <Chart options={deletePeopleChart.options} series={deletePeopleChart.series} type="bar" width={500} height={320} /> : null }
            </ChartContainer>
        </>

    )
}

export default Settings