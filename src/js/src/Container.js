export const Container = prop => (
    <div style ={{width:'1400px',margin : '0 auto', textAlign: "center"}}>
        {prop.children}
    </div>

);

export default Container;