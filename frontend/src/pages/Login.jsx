import { useState } from "react";
import { botonPersonalizado } from "../utils/botones";
import { generarInput } from "../utils/forms";

export default function Login(){
    const [formData, setFormData] = useState({
        'email': '',
        'password': '',
    });
    // const [error, setError] = useState(null);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value}
        ))
    }

    /* async */ function handleSubmit(e){
        e.preventDefault();
        console.log("Datos listos para ser enviados: " + JSON.stringify(formData));
        window.alert("Iniciando...")
    }
    const render = (
        <section className="login container">
            <form className="login-form" onSubmit={handleSubmit}>
                <h1 className="text-center display-6">Inicio de sesion</h1>
                {generarInput({text:"Correo electrónico", name:"email", type:"email", placehFormData:"tuemail@email.com", value: formData.email, onChange: handleChange})}
                {generarInput({text:"Contraseña", name:"password", type:"password", placeholder:"Tu contraseña", value: formData.password, onChange: handleChange})}
                {botonPersonalizado({text:"Iniciar Sesión", type:"submit"})}
            </form>
        </section>
    )
    return(render)
}
