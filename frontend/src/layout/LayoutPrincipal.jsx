import {Outlet} from "react-dom";
import Header from "../components/Header";
import Footer from "../components/Footer"

export default function LayoutPrincipal(){
    return(
        <div className="app-shell bg-blanco">
            <Header/>
            <main className="container">
                <Outlet/>
            </main>
            <Footer/>
        </div>
    );    
}
