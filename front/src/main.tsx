import React from 'react'
import ReactDOM from 'react-dom/client'
import Navbar from "./components/Navbar";
import { globalStyles } from "../styles";
import { RouterProvider } from 'react-router-dom';
import {router} from "./router/Routes";
import {Provider} from "react-redux";
import { store , persistor} from "./store/store";
import { PersistGate } from 'redux-persist/integration/react';

globalStyles()

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
      <Provider store={store}>
          <PersistGate persistor={persistor} loading={null}>
              <Navbar/>
              <RouterProvider router={router}/>
          </PersistGate>
      </Provider>
  </React.StrictMode>,
)
