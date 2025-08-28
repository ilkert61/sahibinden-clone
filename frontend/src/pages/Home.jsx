export default function Home() {
    return (
        <div className="flex flex-col items-center justify-center min-h-[calc(100vh-80px)] bg-gray-50 px-4">
            {/* Hero */}
            <div className="text-center max-w-2xl">
                <h1 className="text-4xl md:text-5xl font-extrabold text-gray-800 mb-4">
                    Sahibinden<span className="text-yellow-500">.clone</span>
                </h1>
                <p className="text-lg md:text-xl text-gray-600 mb-8">
                    Hoş geldin! <br />
                    Menüden giriş yapabilir, kayıt olabilir ya da ürünlere göz
                    atabilirsin.
                </p>

                {/* CTA butonları */}
                <div className="flex flex-wrap gap-4 justify-center">
                    <a
                        href="/products"
                        className="px-6 py-3 bg-yellow-400 hover:bg-yellow-500 text-gray-800 font-semibold rounded-lg shadow transition"
                    >
                        Ürünlere Göz At
                    </a>
                    <a
                        href="/register"
                        className="px-6 py-3 bg-gray-800 hover:bg-gray-900 text-white font-semibold rounded-lg shadow transition"
                    >
                        Hemen Kayıt Ol
                    </a>
                </div>
            </div>

            {/* Alt bilgi */}
            <div className="mt-16 text-gray-500 text-sm">
                🚗 Kendi ilanını ekleyip hemen paylaş!
            </div>
        </div>
    );
}
