import React, { useEffect, useState } from "react";
import "./Home.css";
import Product from "./Product";
import { getAllProdutos } from './services/produtoService';

function Home() {
  const [produtos, setProdutos] = useState({
    secao1: [],
    secao2: [],
    secao3: []
  })

  useEffect(() => {
    getAllProdutos().then(({ data }) => {
      const produtos = data.map(item => ({
        id: item.id,
        title: item.nome,
        price: item.preco,
        rating: item.nota || 5,
        image: item.imagemUrl || 'https://images-na.ssl-images-amazon.com/images/I/51Zymoq7UnL._SX325_BO1,204,203,200_.jpg'
      }))

      if (!produtos.length)
        return

      const state = {
        secao1: [],
        secao2: [],
        secao3: []
      }

      let secao1Preenchida = false
      let secao2Preenchida = false
      let secao3Preenchida = false

      produtos.forEach(element => {
        if (!secao1Preenchida) {
          state.secao1.push(element)
          secao1Preenchida = state.secao1.length === 2
          return
        }

        if (!secao2Preenchida) {
          state.secao2.push(element)
          secao2Preenchida = state.secao2.length === 3
          return
        }

        if (!secao3Preenchida) {
          state.secao3.push(element)
          secao3Preenchida = state.secao3.length === 1
          return
        }
      })

      setProdutos(state)
    })
  }, [])

  return (
    <div className="home">
      <div className="home__container">
        <img
          className="home__image"
          src="https://images-eu.ssl-images-amazon.com/images/G/02/digital/video/merch2016/Hero/Covid19/Generic/GWBleedingHero_ENG_COVIDUPDATE__XSite_1500x600_PV_en-GB._CB428684220_.jpg"
          alt=""
        />

        <div className="home__row">
          {produtos.secao1.map(item => (
            <Product
              id={item.id}
              title={item.title}
              price={item.price}
              rating={item.rating}
              image={item.image}
            />
          ))}

        </div>

        <div className="home__row">
          {produtos.secao2.map(item => (
            <Product
              id={item.id}
              title={item.title}
              price={item.price}
              rating={item.rating}
              image={item.image}
            />
          ))}
        </div>

        <div className="home__row">
          {produtos.secao3.map(item => (
            <Product
              id={item.id}
              title={item.title}
              price={item.price}
              rating={item.rating}
              image={item.image}
            />
          ))}
        </div>
      </div>
    </div>
  );
}

export default Home;
