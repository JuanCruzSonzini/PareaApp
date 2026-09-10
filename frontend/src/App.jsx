import {BrowserRouter, Routes, Route} from "react-dom";
import LayoutPrincipal from "./layout/LayoutPrincipal";
import Login from "./pages/Login";

export default function App(){
  console.log("layout: "  + LayoutPrincipal)
  console.log("login: "  + Login)
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

