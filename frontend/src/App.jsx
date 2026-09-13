import {BrowserRouter, Routes, Route} from "react-router-dom";
import LayoutPrincipal from "./layout/LayoutPrincipal";
import Login from "./pages/Login";

export default function App(){
  return(
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LayoutPrincipal/>}>
          <Route index element={<Login/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

