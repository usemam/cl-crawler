using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using ServiceStack;
using Usemam.Cl.Crawler.AppHost;
using Usemam.Cl.Crawler.Domain.Repositories;

namespace Usemam.Cl.Crawler.Api
{
    public class Startup
    {
        private readonly IConfiguration _configuration;

        public Startup(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public void Configure(
            IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole();

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            var host = new HostBuilder()
                .WithAssembly(typeof(Startup).Assembly)
                .WithAssembly(typeof(RedisUserRepository).Assembly)
                .WithRedis(c => c.WithHostInfo(
                    _configuration["RedisHost"], int.Parse(_configuration["RedisPort"])))
                .BuildWebHost();
            host.AppSettings = new NetCoreAppSettings(_configuration);
            app.UseServiceStack(host);
        }
    }
}
