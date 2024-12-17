import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './App.css'
import AddProject from './pages/AddProject';
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import ProjectList from './pages/ProjectList';
import TicketList from './pages/TicketList';
import { Provider } from 'react-redux';

import store from './store/index';

const router = createBrowserRouter([
  {
    path: '',
    element: <HomePage />,
    children: [
      {
        path: 'projects',
        element: <ProjectList />
      }, 
      {
        path: 'tickets',
        element: <TicketList />
      }
    ]
  },
  {
    path: '/auth',
    element: <AuthPage />
  },
  {
    path: '/projects/add-project',
    element: <AddProject />
  }
]);

function App() {
  return <Provider store={store}><RouterProvider router={router} /></Provider>
}

export default App;
