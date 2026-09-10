// import { useState } from "react";

export default function Login(){
    // const [email, setEmail] = useState('');
    // const [password, setPassword] = useState('');
    // const [loading, setLoading] = useState(false);
    // const [error, setError] = useState(null)

    // async function handleSumbit(e){
    //     e.preventDefault();

    // }
    
    return(
        <section>
            <form>
                <h1 className="text-center display-6">Inicio de sesion</h1>
                <div className="mb-3">
                    <label for="emailInput" className="form-label">Correo electronico</label>
                    <input name="emailIntput" className="form-control" placeholder="tuemail@email.com"></input>
                </div>
            </form>
        </section>
    )
}
