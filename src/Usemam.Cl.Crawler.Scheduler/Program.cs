using System;
using Hangfire;

namespace Usemam.Cl.Crawler.Scheduler
{
    class Program
    {
        static void Main(string[] args)
        {
            string redisHost;
            try
            {
                redisHost = args[0];
            }
            catch
            {
                Console.WriteLine("Redis host is not provided");
                return;
            }

            int redisPort;
            try
            {
                redisPort = int.Parse(args[1]);
            }
            catch
            {
                Console.WriteLine("Redis port is not provided");
                return;
            }

            new AppHost(redisHost, redisPort).Init();

            using (new BackgroundJobServer())
            {
                Console.WriteLine("Press any key to stop...");
                Console.ReadKey();
            }
        }
    }
}
