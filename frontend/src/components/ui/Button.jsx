export default function Button({ children, className = "", ...props }) {
    return (
        <button
            className={`px-4 h-10 rounded-xl bg-brand hover:bg-brandDark text-black font-semibold shadow-sm 
                  disabled:opacity-50 disabled:cursor-not-allowed ${className}`}
            {...props}
        >
            {children}
        </button>
    );
}
