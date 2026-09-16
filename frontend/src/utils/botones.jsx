export function botonPersonalizado({
    text="Texto boton",
    valor="primary",
    type="button",
    onClick
}){
    return(
        <div className="mb-3">
            <button 
                className={`btn btn-${valor} btn-lg`} 
                type={type}
                onClick={onClick}
            >{text}</button>
        </div>
    );
}