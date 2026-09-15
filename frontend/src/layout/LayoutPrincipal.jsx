import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Footer from "../components/Footer"

export default function LayoutPrincipal(){
    return(
        <div className="app-shell">
            <Header />
            <main className="container app-contenedor">
                <Outlet />
            </main>
            <Footer />
        </div>
    );    
}
