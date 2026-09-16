export function botonPersonalizado({
    text="Texto boton",
    valor="primary",
    type="button",
    onClick
}){
    return(
        <div className="mb-3 d-flex flex-column flex-md-row justify-content-md-end">
            <button 
                className={`btn btn-${valor} btn-lg`} 
                type={type}
                onClick={onClick}
            >{text}</button>
        </div>
    );
}