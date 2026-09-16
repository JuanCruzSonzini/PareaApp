// import { useState } from "react";
import { botonPersonalizado } from "../utils/botones";

export default function Login(){
    // const [email, setEmail] = useState('');
    // const [password, setPassword] = useState('');
    // const [loading, setLoading] = useState(false);
    // const [error, setError] = useState(null)

    // async function handleSumbit(e){
    //     e.preventDefault();

    // }
    return(
        <section className="login container">
            <form className="login-form">
                <h1 className="text-center display-6">Inicio de sesion</h1>
                <div className="mb-3">
                    <label for="emailInput" className="form-label">Correo electronico</label>
                    <input name="emailIntput" className="form-control" placeholder="tuemail@email.com"></input>
                </div>
                <div className="mb-3">
                    <label for="passwordInput" className="form-label">Contraseña</label>
                    <input type="password" name="passwordInput" className="form-control" placeholder="Tu Contraseña"/>
                </div>
                <botonPersonalizado text="Iniciar Sesión" type="submit"/>
            </form>
        </section>
    )
}
