using ServiceStack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Usemam.Cl.Crawler.Api.Routes;

namespace Usemam.Cl.Crawler.Api.Services
{
    public class HelloService : Service
    {
        public object Get(Hello _)
        {
            return "Hello, world!";
        }
    }
}
