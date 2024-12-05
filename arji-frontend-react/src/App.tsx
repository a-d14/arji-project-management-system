import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './App.css'
import AddProject from './pages/AddProject';
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';

const router = createBrowserRouter([
  {
    path: '',
    element: <HomePage />
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
  return <RouterProvider router={router} />
}

export default App;
