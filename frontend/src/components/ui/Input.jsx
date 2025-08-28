export default function Input({ className = "", ...props }) {
    return (
        <input
            className={`w-full h-10 px-3 rounded-lg border border-gray-300 bg-white focus:outline-none 
                  focus:ring-2 focus:ring-brand ${className}`}
            {...props}
        />
    );
}
