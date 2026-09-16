export function generarInput({
    text="Texto Label",
    name="Nombre input",
    type="input",
    placeholder="Tu placeholder",
    classLabel="",
    classInput="",
    value,
    onChange
}){
    return(
    <div className="mb-3">
        <label htmlFor={name} className={`form-label ${classLabel}`}>{text}</label>
        <input type={type} name={name} className={`form-control ${classInput}`} placeholder={placeholder} value={value} onChange={onChange}/>
    </div>
    )
}